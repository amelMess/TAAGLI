(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('StageDialogController', StageDialogController);

    StageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Stage', 'Etudiant', 'Entreprise', 'Encadrant', 'Enseignant', 'Principal', 'Auth', 'JhiLanguageService', '$translate'];

    function StageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Stage, Etudiant, Entreprise, Encadrant, Enseignant, Principal, Auth, JhiLanguageService, $translate ) {
        var vm = this;
        vm.stage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.etudiants = Etudiant.query();
        vm.entreprises = Entreprise.query();
        vm.encadrants = Encadrant.query();
        vm.enseignants = Enseignant.query();


        vm.error = null;
        vm.settingsAccount = null;
        vm.success = null;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };
       /* var login = account.login;
        console.log(login);*/

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
            console.log(vm.settingsAccount.login);


        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stage.id !== null) {
                Stage.update(vm.stage, onSaveSuccess, onSaveError);
            } else {
                Stage.save(vm.stage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliApp:stageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDebut = false;
        vm.datePickerOpenStatus.dateFin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
