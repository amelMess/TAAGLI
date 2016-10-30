(function() {
    'use strict';
    angular
        .module('taagliApp')
        .factory('Etudiant', Etudiant);

    Etudiant.$inject = ['$resource', 'DateUtils'];

    function Etudiant ($resource, DateUtils) {
        var resourceUrl =  'api/etudiants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateNaissance = DateUtils.convertDateTimeFromServer(data.dateNaissance);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
