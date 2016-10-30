(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('AdresseDetailController', AdresseDetailController);

    AdresseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Adresse', 'Entreprise'];

    function AdresseDetailController($scope, $rootScope, $stateParams, previousState, entity, Adresse, Entreprise) {
        var vm = this;

        vm.adresse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliApp:adresseUpdate', function(event, result) {
            vm.adresse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
