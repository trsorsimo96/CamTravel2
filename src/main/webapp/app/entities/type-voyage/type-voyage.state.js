(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-voyage', {
            parent: 'entity',
            url: '/type-voyage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.typeVoyage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-voyage/type-voyages.html',
                    controller: 'TypeVoyageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typeVoyage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-voyage-detail', {
            parent: 'type-voyage',
            url: '/type-voyage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.typeVoyage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-voyage/type-voyage-detail.html',
                    controller: 'TypeVoyageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typeVoyage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TypeVoyage', function($stateParams, TypeVoyage) {
                    return TypeVoyage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-voyage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-voyage-detail.edit', {
            parent: 'type-voyage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-voyage/type-voyage-dialog.html',
                    controller: 'TypeVoyageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeVoyage', function(TypeVoyage) {
                            return TypeVoyage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-voyage.new', {
            parent: 'type-voyage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-voyage/type-voyage-dialog.html',
                    controller: 'TypeVoyageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-voyage', null, { reload: 'type-voyage' });
                }, function() {
                    $state.go('type-voyage');
                });
            }]
        })
        .state('type-voyage.edit', {
            parent: 'type-voyage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-voyage/type-voyage-dialog.html',
                    controller: 'TypeVoyageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeVoyage', function(TypeVoyage) {
                            return TypeVoyage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-voyage', null, { reload: 'type-voyage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-voyage.delete', {
            parent: 'type-voyage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-voyage/type-voyage-delete-dialog.html',
                    controller: 'TypeVoyageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeVoyage', function(TypeVoyage) {
                            return TypeVoyage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-voyage', null, { reload: 'type-voyage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
