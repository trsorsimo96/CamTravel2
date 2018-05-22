'use strict';

describe('Controller Tests', function() {

    describe('Voyage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVoyage, MockRoutes, MockCar, MockTrain, MockTypeVoyage, MockStateVoyage, MockBooking;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVoyage = jasmine.createSpy('MockVoyage');
            MockRoutes = jasmine.createSpy('MockRoutes');
            MockCar = jasmine.createSpy('MockCar');
            MockTrain = jasmine.createSpy('MockTrain');
            MockTypeVoyage = jasmine.createSpy('MockTypeVoyage');
            MockStateVoyage = jasmine.createSpy('MockStateVoyage');
            MockBooking = jasmine.createSpy('MockBooking');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Voyage': MockVoyage,
                'Routes': MockRoutes,
                'Car': MockCar,
                'Train': MockTrain,
                'TypeVoyage': MockTypeVoyage,
                'StateVoyage': MockStateVoyage,
                'Booking': MockBooking
            };
            createController = function() {
                $injector.get('$controller')("VoyageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'camTravel2App:voyageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
