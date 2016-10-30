(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EntrepriseDetailController', EntrepriseDetailController);

    EntrepriseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entreprise', 'Stage', 'Adresse', 'Encadrant'];

    function EntrepriseDetailController($scope, $rootScope, $stateParams, previousState, entity, Entreprise, Stage, Adresse, Encadrant) {
        var vm = this;

        vm.entreprise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliApp:entrepriseUpdate', function(event, result) {
            vm.entreprise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
