(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mode-payment', {
            parent: 'entity',
            url: '/mode-payment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.modePayment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mode-payment/mode-payments.html',
                    controller: 'ModePaymentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('modePayment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mode-payment-detail', {
            parent: 'mode-payment',
            url: '/mode-payment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'camTravel2App.modePayment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mode-payment/mode-payment-detail.html',
                    controller: 'ModePaymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('modePayment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ModePayment', function($stateParams, ModePayment) {
                    return ModePayment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mode-payment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mode-payment-detail.edit', {
            parent: 'mode-payment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode-payment/mode-payment-dialog.html',
                    controller: 'ModePaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModePayment', function(ModePayment) {
                            return ModePayment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mode-payment.new', {
            parent: 'mode-payment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode-payment/mode-payment-dialog.html',
                    controller: 'ModePaymentDialogController',
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
                    $state.go('mode-payment', null, { reload: 'mode-payment' });
                }, function() {
                    $state.go('mode-payment');
                });
            }]
        })
        .state('mode-payment.edit', {
            parent: 'mode-payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode-payment/mode-payment-dialog.html',
                    controller: 'ModePaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModePayment', function(ModePayment) {
                            return ModePayment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mode-payment', null, { reload: 'mode-payment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mode-payment.delete', {
            parent: 'mode-payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mode-payment/mode-payment-delete-dialog.html',
                    controller: 'ModePaymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ModePayment', function(ModePayment) {
                            return ModePayment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mode-payment', null, { reload: 'mode-payment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
