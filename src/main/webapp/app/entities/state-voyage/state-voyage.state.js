(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('state-voyage', {
            parent: 'entity',
            url: '/state-voyage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.stateVoyage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/state-voyage/state-voyages.html',
                    controller: 'StateVoyageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('stateVoyage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('state-voyage-detail', {
            parent: 'state-voyage',
            url: '/state-voyage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.stateVoyage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/state-voyage/state-voyage-detail.html',
                    controller: 'StateVoyageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('stateVoyage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StateVoyage', function($stateParams, StateVoyage) {
                    return StateVoyage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'state-voyage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('state-voyage-detail.edit', {
            parent: 'state-voyage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state-voyage/state-voyage-dialog.html',
                    controller: 'StateVoyageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StateVoyage', function(StateVoyage) {
                            return StateVoyage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('state-voyage.new', {
            parent: 'state-voyage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state-voyage/state-voyage-dialog.html',
                    controller: 'StateVoyageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('state-voyage', null, { reload: 'state-voyage' });
                }, function() {
                    $state.go('state-voyage');
                });
            }]
        })
        .state('state-voyage.edit', {
            parent: 'state-voyage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state-voyage/state-voyage-dialog.html',
                    controller: 'StateVoyageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StateVoyage', function(StateVoyage) {
                            return StateVoyage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('state-voyage', null, { reload: 'state-voyage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('state-voyage.delete', {
            parent: 'state-voyage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state-voyage/state-voyage-delete-dialog.html',
                    controller: 'StateVoyageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StateVoyage', function(StateVoyage) {
                            return StateVoyage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('state-voyage', null, { reload: 'state-voyage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
