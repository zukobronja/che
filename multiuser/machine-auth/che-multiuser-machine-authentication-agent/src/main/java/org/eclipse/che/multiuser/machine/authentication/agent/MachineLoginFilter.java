/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.multiuser.machine.authentication.agent;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.che.commons.auth.token.RequestTokenExtractor;
import org.eclipse.che.commons.env.EnvironmentContext;
import org.eclipse.che.commons.subject.Subject;
import org.eclipse.che.commons.subject.SubjectImpl;

/**
 * Protects user's machine from unauthorized access.
 *
 * @author Anton Korneta
 */
@Singleton
public class MachineLoginFilter implements Filter {

  private static final Gson GSON = new Gson();

  private static final String MACHINE_TOKEN_KIND = "machine_token";
  private static final String MACHINE_TOKEN_ENV = "CHE_MACHINE_TOKEN";
  private static final String SIGNATURE_PUBLIC_KEY_ENV = "SIGNATURE_PUBLIC_KEY";

  private PublicKey publicKey;
  private Subject machineOwner;

  @Inject private RequestTokenExtractor tokenExtractor;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    final HttpSession session = httpRequest.getSession(false);

    // sets subject from session
    if (session != null && session.getAttribute("principal") != null) {
      try {
        EnvironmentContext.getCurrent().setSubject((Subject) session.getAttribute("principal"));
        chain.doFilter(request, response);
        return;
      } finally {
        EnvironmentContext.reset();
      }
    }

    // retrieves a token from a request and checks it
    final String token = tokenExtractor.getToken(httpRequest);
    if (isNullOrEmpty(token)) {
      sendErr(response, SC_UNAUTHORIZED, "Authentication on machine failed, token is missed.");
      return;
    }

    // checks token signature + user id if ok then sets subject to context
    try {
      final PublicKey signatureKey = getSignatureKey();
      final Jwt jwt = Jwts.parser().setSigningKey(signatureKey).parse(token);
      if (MACHINE_TOKEN_KIND.equals(jwt.getHeader().get("kind"))) {
        final SubjectImpl subject = GSON.fromJson(jwt.getBody().toString(), SubjectImpl.class);
        if (!isMachineOwner(subject)) {
          sendErr(
              response,
              SC_FORBIDDEN,
              String.format("User '%s' have no access to this machine.", subject.getUserId()));
        }
        try {
          EnvironmentContext.getCurrent().setSubject(subject);
          final HttpSession httpSession = httpRequest.getSession(true);
          httpSession.setAttribute("principal", subject);
          chain.doFilter(request, response);
        } finally {
          EnvironmentContext.reset();
        }
      } else {
        sendErr(response, SC_UNAUTHORIZED, "Authentication on machine failed, token is missed.");
      }
    } catch (RuntimeException ex) {
      sendErr(
          response,
          SC_UNAUTHORIZED,
          format("Authentication on machine failed cause: '%s'", ex.getMessage()));
    }
  }

  /** Checks whether given subject is owner of this machine or not. */
  private boolean isMachineOwner(Subject toTest) {
    if (machineOwner == null) {
      final String token = System.getenv().get(MACHINE_TOKEN_ENV);
      final Jwt jwt = Jwts.parser().setSigningKey(getSignatureKey()).parse(token);
      machineOwner = GSON.fromJson(jwt.getBody().toString(), SubjectImpl.class);
    }
    return machineOwner.getUserId().equals(toTest.getUserId());
  }

  /**
   * Returns public key from machine environment.
   *
   * @throws IllegalStateException when any error occurs while token retrieving
   */
  private PublicKey getSignatureKey() throws IllegalStateException {
    if (publicKey != null) {
      return publicKey;
    }
    try {
      final String envVariable = System.getenv().get(SIGNATURE_PUBLIC_KEY_ENV);
      if (Strings.isNullOrEmpty(envVariable)) {
        throw new IllegalStateException("Signature key for token validation is not found");
      }
      final X509EncodedKeySpec keySpec =
          new X509EncodedKeySpec(Base64.getDecoder().decode(envVariable));
      final KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
      this.publicKey = rsaKeyFactory.generatePublic(keySpec);
      return publicKey;
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new IllegalStateException(ex.getCause());
    }
  }

  /** Sets given error code with err message into give response. */
  private static void sendErr(ServletResponse res, int errCode, String msg) throws IOException {
    final HttpServletResponse response = (HttpServletResponse) res;
    response.sendError(errCode, msg);
  }

  @Override
  public void destroy() {}
}
