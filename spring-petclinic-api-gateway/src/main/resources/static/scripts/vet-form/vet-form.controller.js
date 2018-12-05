'use strict';

angular.module('vetForm')
    .controller('VetFormController', ["$http", '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;
        var vetId = $stateParams.vetId || 0;

        if (!vetId) {
            self.vet = {};
        } else {
            $http.get("api/vet/vets/" + vetId).then(function (resp) {
                self.vet = resp.data;
                self.specialtiesHolder  = "";
                var specialities = self.vet.specialties;

                for (var i = 0; i < specialities.length; i++) {
                    self.specialtiesHolder += specialities[i].name + " ";
                }
            });
        }

        self.submitVetForm = function () {
            var id = self.vet.id;
            var specialities = self.specialtiesHolder.split(" ");

            self.vet.specialties = specialities.map(function(name) {
                return { "name": name }
            });

            var req;
            req = $http.put("api/vet/vets/" + id, self.vet);


            req.then(function () {
                $state.go('vets');
            }, function (response) {
                var error = response.data;
                alert(error.error + "\r\n" + error.errors.map(function (e) {
                    return e.field + ": " + e.defaultMessage;
                }).join("\r\n"));
            });
        };
    }]);
