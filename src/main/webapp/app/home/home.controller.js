(function() {
    'use strict';

    angular
        .module('taagliApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', 'Principal'];

    function HomeController ($rootScope, $state, $timeout, Auth, Principal) {
        //console.log("aroua");
        var vm = this;

        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;

        vm.isAuthenticated = Principal.isAuthenticated();

        if(vm.isAuthenticated)
            $state.go('stage');

        $timeout(function (){angular.element('#username').focus();});

        function cancel () {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
            //$uibModalInstance.dismiss('cancel');
        }

        function login (event) {

            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                /*vm.authenticationError = false;
                 $uibModalInstance.close();
                 if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                 $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                 $state.go('home');
                 }

                 $rootScope.$broadcast('authenticationSuccess');

                 // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                 // since login is succesful, go to stored previousState and clear previousState
                 if (Auth.getPreviousState()) {
                 var previousState = Auth.getPreviousState();
                 Auth.resetPreviousState();
                 $state.go(previousState.name, previousState.params);
                 }*/
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function register () {
            //$uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword () {
            //$uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
