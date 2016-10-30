(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EncadrantDeleteController',EncadrantDeleteController);

    EncadrantDeleteController.$inject = ['$uibModalInstance', 'entity', 'Encadrant'];

    function EncadrantDeleteController($uibModalInstance, entity, Encadrant) {
        var vm = this;

        vm.encadrant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Encadrant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
