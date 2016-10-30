'use strict';

describe('Controller Tests', function() {

    describe('Entreprise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEntreprise, MockStage, MockAdresse, MockEncadrant;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEntreprise = jasmine.createSpy('MockEntreprise');
            MockStage = jasmine.createSpy('MockStage');
            MockAdresse = jasmine.createSpy('MockAdresse');
            MockEncadrant = jasmine.createSpy('MockEncadrant');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Entreprise': MockEntreprise,
                'Stage': MockStage,
                'Adresse': MockAdresse,
                'Encadrant': MockEncadrant
            };
            createController = function() {
                $injector.get('$controller')("EntrepriseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'taagliApp:entrepriseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
