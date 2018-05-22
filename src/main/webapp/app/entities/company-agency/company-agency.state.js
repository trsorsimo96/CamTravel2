(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('company-agency', {
            parent: 'entity',
            url: '/company-agency',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.companyAgency.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company-agency/company-agencies.html',
                    controller: 'CompanyAgencyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('companyAgency');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('company-agency-detail', {
            parent: 'company-agency',
            url: '/company-agency/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.companyAgency.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company-agency/company-agency-detail.html',
                    controller: 'CompanyAgencyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('companyAgency');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CompanyAgency', function($stateParams, CompanyAgency) {
                    return CompanyAgency.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'company-agency',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('company-agency-detail.edit', {
            parent: 'company-agency-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-agency/company-agency-dialog.html',
                    controller: 'CompanyAgencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompanyAgency', function(CompanyAgency) {
                            return CompanyAgency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company-agency.new', {
            parent: 'company-agency',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-agency/company-agency-dialog.html',
                    controller: 'CompanyAgencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                company: null,
                                agency: null,
                                commision: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('company-agency', null, { reload: 'company-agency' });
                }, function() {
                    $state.go('company-agency');
                });
            }]
        })
        .state('company-agency.edit', {
            parent: 'company-agency',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-agency/company-agency-dialog.html',
                    controller: 'CompanyAgencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompanyAgency', function(CompanyAgency) {
                            return CompanyAgency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company-agency', null, { reload: 'company-agency' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company-agency.delete', {
            parent: 'company-agency',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-agency/company-agency-delete-dialog.html',
                    controller: 'CompanyAgencyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CompanyAgency', function(CompanyAgency) {
                            return CompanyAgency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company-agency', null, { reload: 'company-agency' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
