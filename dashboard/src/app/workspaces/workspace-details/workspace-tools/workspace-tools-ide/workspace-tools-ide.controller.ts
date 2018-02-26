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
import {IPackage, ISearchResults, NpmRegistry} from '../../../../../components/api/npm-registry.factory';
import {WorkspaceDetailsToolsService} from '../workspace-details-tools.service';

/**
 * @ngdoc controller
 * @name workspaces.details.tools.controller:WorkspaceDetailsToolsIdeController
 * @description This class is handling the controller for details of workspace ide tool.
 * @author Ann Shumilova
 */
export class WorkspaceToolsIdeController {
  static $inject = ['npmRegistry', 'lodash', 'cheListHelperFactory', '$scope', 'workspaceDetailsToolsService'];
  private cheListHelper: che.widget.ICheListHelper;

  packageOrderBy = 'name';
  packages: Array<IPackage>;
  packagesSummary: ISearchResults;
  packagesFilter: any;
  workspaceDetailsToolsService: WorkspaceDetailsToolsService;

  /**
   * Default constructor that is using resource
   */
  constructor(npmRegistry: NpmRegistry, lodash: any, cheListHelperFactory: che.widget.ICheListHelperFactory,
              $scope: ng.IScope, workspaceDetailsToolsService: WorkspaceDetailsToolsService) {
    const helperId = 'workspace-tools-ide';
    this.workspaceDetailsToolsService = workspaceDetailsToolsService;

    this.cheListHelper = cheListHelperFactory.getHelper(helperId);
    $scope.$on('$destroy', () => {
      cheListHelperFactory.removeHelper(helperId);
    });
    this.packagesFilter = {name: ''};

    npmRegistry.search('keywords:theia-extension').then((data: ISearchResults) => {
      this.packagesSummary = data;
      this.packages = lodash.pluck(this.packagesSummary.results, 'package');
      this.packages.forEach((_package: IPackage) => {
        _package.isEnabled = true;
      });
      this.cheListHelper.setList(this.packages, 'name');
    });

    //this.this.workspaceDetailsToolsService.getCurrentMachine());
  }


  /**
   * Callback when name is changed.
   *
   * @param str {string} a string to filter projects names
   */
  onSearchChanged(str: string): void {
    this.packagesFilter.name = str;
    this.cheListHelper.applyFilter('name', this.packagesFilter);
  }

 /* private isPackageEnabled(): boolean {
    this.workspaceDetailsToolsService.getCurrentMachine();
  }*/
}
