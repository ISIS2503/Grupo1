/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * ALERTA CON PAGINACION DEL LADO DEL CLIENTE
 */
(function (ng) {
    //Se agrega la dependencia simplePagination al modulo
    var alert = angular.module('alertas', ['ui.router','ngRoute']);
    alert.directive('alertToolbar', function () {
        console.log("Passed, adding toolbar");
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
    });
    alert.directive('alertInfo', function () {
        return{
            restrict: 'E',
            templateUrl: 'aleratInfo.html',
            controller: ['$http', '$scope', function ($http, $scope) {
                    var self = this;
                    self.alerts=[];
                    $scope.api = function () {
                        /**
                         * CAMBIAR A LA DE ALERTAS DE PEDRO
                         */
                        var callApi = $http.get('http://172.24.42.34:9000/webresources/competitors').succes(function(data){
                          self.alerts=data();
                        });
                    };
                    $scope.api();
                }],
            controllerAs:'AlertsListCtrl'
        };
    });
    alert.controller('alertasCtrl', ['$scope', '$http',
        function alertasCtrl($scope, $http) {
            //CAMBIAR A LA DE ALERTAS DE PEDRO
                var callApi = $http.get('http://localhost:8083/webresources/competitors').success(function (data) {
                    $scope.alerts = data;                   
                });
                callApi.then(function () {
                    $scope.totalR = $scope.alerts;
                });   
        }
    ]);
     alert.directive('datatableSetup', ['$timeout',
        function ($timeout) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
 
                    $timeout(function () {
                        element.dataTable();
                    });
                }
            };
        }
    ]);
})(window.angular);
