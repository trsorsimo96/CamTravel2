(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-passenger', {
            parent: 'entity',
            url: '/type-passenger',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.typePassenger.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-passenger/type-passengers.html',
                    controller: 'TypePassengerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typePassenger');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-passenger-detail', {
            parent: 'type-passenger',
            url: '/type-passenger/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.typePassenger.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-passenger/type-passenger-detail.html',
                    controller: 'TypePassengerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typePassenger');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TypePassenger', function($stateParams, TypePassenger) {
                    return TypePassenger.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-passenger',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-passenger-detail.edit', {
            parent: 'type-passenger-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-passenger/type-passenger-dialog.html',
                    controller: 'TypePassengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePassenger', function(TypePassenger) {
                            return TypePassenger.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-passenger.new', {
            parent: 'type-passenger',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-passenger/type-passenger-dialog.html',
                    controller: 'TypePassengerDialogController',
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
                    $state.go('type-passenger', null, { reload: 'type-passenger' });
                }, function() {
                    $state.go('type-passenger');
                });
            }]
        })
        .state('type-passenger.edit', {
            parent: 'type-passenger',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-passenger/type-passenger-dialog.html',
                    controller: 'TypePassengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePassenger', function(TypePassenger) {
                            return TypePassenger.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-passenger', null, { reload: 'type-passenger' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-passenger.delete', {
            parent: 'type-passenger',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-passenger/type-passenger-delete-dialog.html',
                    controller: 'TypePassengerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePassenger', function(TypePassenger) {
                            return TypePassenger.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-passenger', null, { reload: 'type-passenger' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
