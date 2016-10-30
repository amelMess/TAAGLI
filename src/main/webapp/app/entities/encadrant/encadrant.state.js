(function() {
    'use strict';

    angular
        .module('taagliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('encadrant', {
            parent: 'entity',
            url: '/encadrant',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'taagliApp.encadrant.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/encadrant/encadrants.html',
                    controller: 'EncadrantController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('encadrant');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('encadrant-detail', {
            parent: 'entity',
            url: '/encadrant/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'taagliApp.encadrant.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/encadrant/encadrant-detail.html',
                    controller: 'EncadrantDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('encadrant');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Encadrant', function($stateParams, Encadrant) {
                    return Encadrant.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'encadrant',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('encadrant-detail.edit', {
            parent: 'encadrant-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/encadrant/encadrant-dialog.html',
                    controller: 'EncadrantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Encadrant', function(Encadrant) {
                            return Encadrant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('encadrant.new', {
            parent: 'encadrant',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/encadrant/encadrant-dialog.html',
                    controller: 'EncadrantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nomEnc: null,
                                prenomEnc: null,
                                telEnc: null,
                                mailEnc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('encadrant', null, { reload: 'encadrant' });
                }, function() {
                    $state.go('encadrant');
                });
            }]
        })
        .state('encadrant.edit', {
            parent: 'encadrant',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/encadrant/encadrant-dialog.html',
                    controller: 'EncadrantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Encadrant', function(Encadrant) {
                            return Encadrant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('encadrant', null, { reload: 'encadrant' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('encadrant.delete', {
            parent: 'encadrant',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/encadrant/encadrant-delete-dialog.html',
                    controller: 'EncadrantDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Encadrant', function(Encadrant) {
                            return Encadrant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('encadrant', null, { reload: 'encadrant' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
