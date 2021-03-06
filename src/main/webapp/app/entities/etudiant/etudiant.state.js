(function() {
    'use strict';

    angular
        .module('taagliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('etudiant', {
            parent: 'entity',
            url: '/etudiant',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'taagliApp.etudiant.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/etudiant/etudiants.html',
                    controller: 'EtudiantController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('etudiant');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('etudiant-detail', {
            parent: 'entity',
            url: '/etudiant/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'taagliApp.etudiant.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/etudiant/etudiant-detail.html',
                    controller: 'EtudiantDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('etudiant');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Etudiant', function($stateParams, Etudiant) {
                    return Etudiant.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'etudiant',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('etudiant-detail.edit', {
            parent: 'etudiant-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etudiant/etudiant-dialog.html',
                    controller: 'EtudiantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Etudiant', function(Etudiant) {
                            return Etudiant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('etudiant.new', {
            parent: 'etudiant',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etudiant/etudiant-dialog.html',
                    controller: 'EtudiantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifiant: null,
                                nom: null,
                                prenom: null,
                                sexe: null,
                                tel: null,
                                mail: null,
                                adresse: null,
                                dateNaissance: null,
                                lieuNaissance: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('etudiant', null, { reload: 'etudiant' });
                }, function() {
                    $state.go('etudiant');
                });
            }]
        })
        .state('etudiant.edit', {
            parent: 'etudiant',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etudiant/etudiant-dialog.html',
                    controller: 'EtudiantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Etudiant', function(Etudiant) {
                            return Etudiant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('etudiant', null, { reload: 'etudiant' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('etudiant.delete', {
            parent: 'etudiant',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etudiant/etudiant-delete-dialog.html',
                    controller: 'EtudiantDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Etudiant', function(Etudiant) {
                            return Etudiant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('etudiant', null, { reload: 'etudiant' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
