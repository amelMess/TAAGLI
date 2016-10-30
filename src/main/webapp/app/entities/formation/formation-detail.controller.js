(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('FormationDetailController', FormationDetailController);

    FormationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Formation', 'Etudiant'];

    function FormationDetailController($scope, $rootScope, $stateParams, previousState, entity, Formation, Etudiant) {
        var vm = this;

        vm.formation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliApp:formationUpdate', function(event, result) {
            vm.formation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
