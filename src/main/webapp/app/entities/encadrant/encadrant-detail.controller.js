(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EncadrantDetailController', EncadrantDetailController);

    EncadrantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Encadrant', 'Entreprise', 'Stage'];

    function EncadrantDetailController($scope, $rootScope, $stateParams, previousState, entity, Encadrant, Entreprise, Stage) {
        var vm = this;

        vm.encadrant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliApp:encadrantUpdate', function(event, result) {
            vm.encadrant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
