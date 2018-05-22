'use strict';

describe('Controller Tests', function() {

    describe('Booking Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBooking, MockPassenger, MockVoyage, MockAgency, MockCompany, MockModePayment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBooking = jasmine.createSpy('MockBooking');
            MockPassenger = jasmine.createSpy('MockPassenger');
            MockVoyage = jasmine.createSpy('MockVoyage');
            MockAgency = jasmine.createSpy('MockAgency');
            MockCompany = jasmine.createSpy('MockCompany');
            MockModePayment = jasmine.createSpy('MockModePayment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Booking': MockBooking,
                'Passenger': MockPassenger,
                'Voyage': MockVoyage,
                'Agency': MockAgency,
                'Company': MockCompany,
                'ModePayment': MockModePayment
            };
            createController = function() {
                $injector.get('$controller')("BookingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'camTravel2App:bookingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
