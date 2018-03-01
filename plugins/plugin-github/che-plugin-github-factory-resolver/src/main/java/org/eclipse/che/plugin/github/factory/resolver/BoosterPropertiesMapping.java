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
package org.eclipse.che.plugin.github.factory.resolver;

import static org.eclipse.che.dto.server.DtoFactory.newDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.eclipse.che.api.workspace.shared.dto.CommandDto;
import org.eclipse.che.api.workspace.shared.dto.ServerConfigDto;

/** @author Florent Benoit */
public class BoosterPropertiesMapping {

  private static final String FUSE_BOOSTER = "Fuse Spring Boot Circuit Breaker Example";

  private final Map<String, List<CommandDto>> hardCodedCommands;

  private final Map<String, String> hardCodedImages;

  private final Map<String, Map<String, ServerConfigDto>> hardcodedServers;

  @Inject
  public BoosterPropertiesMapping() {
    this.hardCodedCommands = new HashMap();
    this.hardCodedImages = new HashMap();
    this.hardcodedServers = new HashMap<>();

    CommandDto buildProject =
        newDto(CommandDto.class)
            .withName("Build project")
            .withCommandLine("mvn  -f ${current.project.path} clean package")
            .withType("mvn")
            .withAttributes(Collections.singletonMap("goal", "Run"));
    CommandDto runNameService =
        newDto(CommandDto.class)
            .withName("Run Name Service")
            .withCommandLine(
                "cd ${current.project.path}/name-service && mvn spring-boot:run -Dserver.port=8081")
            .withType("custom")
            .withAttributes(Collections.singletonMap("goal", "Run"));
    Map<String, String> attrs = new HashMap<>();
    attrs.put("goal", "Run");
    attrs.put("previewUrl", "${server.greeter-service}");

    CommandDto runGreetingService =
        newDto(CommandDto.class)
            .withName("Run Greeting Service")
            .withCommandLine("cd ${current.project.path}/greetings-service && mvn spring-boot:run")
            .withType("custom")
            .withAttributes(attrs);

    this.hardCodedCommands.put(
        FUSE_BOOSTER, Arrays.asList(buildProject, runNameService, runGreetingService));

    this.hardCodedImages.put(FUSE_BOOSTER, "florentbenoit/fuse-image");

    Map<String, ServerConfigDto> fuseServers = new HashMap<>();
    ServerConfigDto serverConfigDtoGreeter =
        newDto(ServerConfigDto.class).withPort("8080").withProtocol("http");
    fuseServers.put("greeter-service", serverConfigDtoGreeter);
    ServerConfigDto serverConfigDtoName =
        newDto(ServerConfigDto.class).withPort("8081").withProtocol("http");
    fuseServers.put("name-service", serverConfigDtoName);
    this.hardcodedServers.put(FUSE_BOOSTER, fuseServers);
  }

  public List<CommandDto> getCommands(String boosterName) {
    List<CommandDto> commandDtoList = new ArrayList<>();
    if (hardCodedCommands.containsKey(boosterName)) {
      return hardCodedCommands.get(boosterName);
    }

    return commandDtoList;
  }

  public String getImageName(String boosterName) {
    return hardCodedImages.get(boosterName);
  }

  public Map<String, ServerConfigDto> getServers(String boosterName) {
    Map<String, ServerConfigDto> servers = new HashMap<>();
    if (hardcodedServers.containsKey(boosterName)) {
      return hardcodedServers.get(boosterName);
    }

    return servers;
  }
}
