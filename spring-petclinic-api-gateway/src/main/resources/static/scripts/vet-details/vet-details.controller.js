'use strict';

angular.module('vetDetails')
    .controller('VetDetailsController', ['$http', '$stateParams', function ($http, $stateParams) {
        var self = this;

        $http.get('api/vet/vets/' + $stateParams.vetId).then(function (resp) {
            self.vet = resp.data;
        });
    }]);
