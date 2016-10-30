(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('StageDetailController', StageDetailController);

    StageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stage', 'Etudiant', 'Entreprise', 'Encadrant', 'Enseignant'];

    function StageDetailController($scope, $rootScope, $stateParams, previousState, entity, Stage, Etudiant, Entreprise, Encadrant, Enseignant) {
        var vm = this;

        vm.stage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliApp:stageUpdate', function(event, result) {
            vm.stage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
