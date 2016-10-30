(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('AdresseController', AdresseController);

    AdresseController.$inject = ['$scope', '$state', 'Adresse'];

    function AdresseController ($scope, $state, Adresse) {
        var vm = this;
        
        vm.adresses = [];

        loadAll();

        function loadAll() {
            Adresse.query(function(result) {
                vm.adresses = result;
            });
        }
    }
})();
