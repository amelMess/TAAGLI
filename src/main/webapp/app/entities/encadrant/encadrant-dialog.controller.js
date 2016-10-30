(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('EncadrantDialogController', EncadrantDialogController);

    EncadrantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Encadrant', 'Entreprise', 'Stage'];

    function EncadrantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Encadrant, Entreprise, Stage) {
        var vm = this;

        vm.encadrant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.entreprises = Entreprise.query();
        vm.stages = Stage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.encadrant.id !== null) {
                Encadrant.update(vm.encadrant, onSaveSuccess, onSaveError);
            } else {
                Encadrant.save(vm.encadrant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliApp:encadrantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
