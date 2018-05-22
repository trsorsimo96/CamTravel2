'use strict';

describe('Controller Tests', function() {

    describe('ModelCar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockModelCar, MockCar, MockWagon;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockModelCar = jasmine.createSpy('MockModelCar');
            MockCar = jasmine.createSpy('MockCar');
            MockWagon = jasmine.createSpy('MockWagon');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ModelCar': MockModelCar,
                'Car': MockCar,
                'Wagon': MockWagon
            };
            createController = function() {
                $injector.get('$controller')("ModelCarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'camTravel2App:modelCarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
