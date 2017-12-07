/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function () {
    //Se agrega la dependencia simplePagination al modulo
    var aplicacionMonitoreo = angular.module('losminerales', ['ui.router','alertas','measurement']);
    aplicacionMonitoreo.config(function($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/losminerales");

        $stateProvider
             .state('losminerales', {
                url: "/losminerales",
                templateUrl: "index.html"
            })
            .state('alertas', {
                url: "/alertas",
                templateUrl: "alertas.html"
            })
            .state('measurements', {
                url: "/measurements",
                templateUrl: "measurements.html"
            });
    });
 aplicacionMonitoreo.directive('toolbar', function () {
        return{
            restrict: 'E',
            templateUrl: 'toolbar.html',
            controller: function () {
                this.tab = 0;
                this.selectTab = function (setTab) {
                    this.tab = setTab;
                };
                this.isSelected = function (tabParam) {
                    return this.tab === tabParam;
                };
            },
            controllerAs: 'toolbar'
        };
    })
})();