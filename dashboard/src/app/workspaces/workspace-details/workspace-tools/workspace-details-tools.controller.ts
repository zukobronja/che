/*
 * Copyright (c) 2015-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
'use strict';
import {WorkspaceDetailsToolsService} from './workspace-details-tools.service';
import {IEnvironmentManagerMachine} from '../../../../components/api/environment/environment-manager-machine';

/**
 * @ngdoc controller
 * @name workspaces.details.tools.controller:WorkspaceDetailsToolsController
 * @description This class is handling the controller for details of workspace : section tools
 * @author Ann Shumilova
 */
export class WorkspaceDetailsToolsController {
  static $inject = ['workspaceDetailsToolsService'];

  private workspaceDetailsToolsService: WorkspaceDetailsToolsService;
  private selectedMachine: IEnvironmentManagerMachine;
  private toolType: string;
  private page: string;

  /**
   * Default constructor that is using resource
   */
  constructor(workspaceDetailsToolsService: WorkspaceDetailsToolsService) {
    this.workspaceDetailsToolsService = workspaceDetailsToolsService;
    this.workspaceDetailsToolsService.setCurrentMachine(this.selectedMachine);
    this.toolType = this.workspaceDetailsToolsService.detectToolType(this.selectedMachine);
    if (this.toolType) {
      this.page = this.workspaceDetailsToolsService.getToolConfigPage(this.toolType);
    }
  }
}
