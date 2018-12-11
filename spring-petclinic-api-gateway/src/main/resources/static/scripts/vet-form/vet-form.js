'use strict';

angular.module('vetForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('vetEdit', {
                parent: 'app',
                url: '/vet/:vetId/edit',
                template: '<vet-form></vet-form>'
            })
    }]);
