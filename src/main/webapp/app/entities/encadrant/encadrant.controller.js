(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EncadrantController', EncadrantController);

    EncadrantController.$inject = ['$scope', '$state', 'Encadrant'];

    function EncadrantController ($scope, $state, Encadrant) {
        var vm = this;
        
        vm.encadrants = [];

        loadAll();

        function loadAll() {
            Encadrant.query(function(result) {
                vm.encadrants = result;
            });
        }
    }
})();
