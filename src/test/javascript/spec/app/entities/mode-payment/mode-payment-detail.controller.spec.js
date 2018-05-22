'use strict';

describe('Controller Tests', function() {

    describe('ModePayment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockModePayment, MockBooking, MockDeposit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockModePayment = jasmine.createSpy('MockModePayment');
            MockBooking = jasmine.createSpy('MockBooking');
            MockDeposit = jasmine.createSpy('MockDeposit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ModePayment': MockModePayment,
                'Booking': MockBooking,
                'Deposit': MockDeposit
            };
            createController = function() {
                $injector.get('$controller')("ModePaymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'camTravel2App:modePaymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
