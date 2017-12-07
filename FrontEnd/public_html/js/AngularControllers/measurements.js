/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MEDICIONES CON PAGINACION DEL LADO DEL SEVIDOR
 */
(function (ng) {
    var med = angular.module('measurements', []);
    med.directive('measurementToolbar', function () {
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
    med.directive('medInfo', function () {
        return{
            restrict: 'E',
            templateUrl: 'measurementInfo.html',
            controller: ['$http', '$scope', function ($http, $scope) {
                    var self = this;
                    self.measurements=[];
                    $scope.api=function(){
                        //CAMBIAR A LA DE MEDIDAS DE PEDRO
                    var callApi = $http.get('http://172.24.42.34:9000/measurement', headers
                        {
                            'Access-Control-Allow-Origin': '*'
                            'Access-Control-Allow-Credentials': 'true'
                            'Access-Control-Expose-Headers': 'FooBar'
                        }).succes(function(data){
                          self.measurements=data();
                        });    
                    };
                    $scope.api();
 
                }],
            controllerAs: 'MedsListCtrl'
        };
    });
    med.controller('MyCtrl', ['$scope', '$http',
        function MyCtrl($scope, $http) {
            //CAMBIAR A LA DE MEDIDAS DE PEDRO
                var callApi = $http.get('http://172.24.42.34:9000/measurement', headers
                        {
                            'Access-Control-Allow-Origin': '*'
                            'Access-Control-Allow-Credentials': 'true'
                            'Access-Control-Expose-Headers': 'FooBar'
                        }).success(function (data) {
                    $scope.measurements = data;                   
                });
                callApi.then(function () {
                    $scope.totalR = $scope.measurements;
                });   
        }
    ]);
     med.directive('datatableSetup', ['$timeout',
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
