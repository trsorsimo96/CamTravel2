'use strict';

describe('Controller Tests', function() {

    describe('Wagon Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWagon, MockModelCar, MockTrain, MockClasse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWagon = jasmine.createSpy('MockWagon');
            MockModelCar = jasmine.createSpy('MockModelCar');
            MockTrain = jasmine.createSpy('MockTrain');
            MockClasse = jasmine.createSpy('MockClasse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Wagon': MockWagon,
                'ModelCar': MockModelCar,
                'Train': MockTrain,
                'Classe': MockClasse
            };
            createController = function() {
                $injector.get('$controller')("WagonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'camTravel2App:wagonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
