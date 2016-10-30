(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EnqueteDetailController', EnqueteDetailController);

    EnqueteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Enquete', 'Etudiant'];

    function EnqueteDetailController($scope, $rootScope, $stateParams, previousState, entity, Enquete, Etudiant) {
        var vm = this;

        vm.enquete = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliApp:enqueteUpdate', function(event, result) {
            vm.enquete = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
