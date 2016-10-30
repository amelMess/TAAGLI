(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('AdresseDeleteController',AdresseDeleteController);

    AdresseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Adresse'];

    function AdresseDeleteController($uibModalInstance, entity, Adresse) {
        var vm = this;

        vm.adresse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Adresse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
