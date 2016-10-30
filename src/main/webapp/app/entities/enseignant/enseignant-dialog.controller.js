(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EnseignantDialogController', EnseignantDialogController);

    EnseignantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Enseignant', 'Stage'];

    function EnseignantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Enseignant, Stage) {
        var vm = this;

        vm.enseignant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stages = Stage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.enseignant.id !== null) {
                Enseignant.update(vm.enseignant, onSaveSuccess, onSaveError);
            } else {
                Enseignant.save(vm.enseignant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliApp:enseignantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
