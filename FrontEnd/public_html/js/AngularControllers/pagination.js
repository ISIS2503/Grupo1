/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Archivo pagination.js
(function() {
  "use strict";
 
  var paginationModule = angular.module('simplePagination', []);
 
  paginationModule.factory('Pagination', function() {
 
    var pagination = {};
 
    pagination.getNew = function(perPage) {
 
      perPage = perPage === undefined ? 10 : perPage;
 
      var pagination = {};
 
      var paginator = {
        numPages: 1,
        perPage: perPage,
        page: 1
      }; // Prototipo de objeto pagination
      paginator.setSize= function(size){
          paginator.perPage=size;
    };
      paginator.prevPage = function() {
        if (paginator.page > 1) {
          paginator.page -= 1;
        }
      };//Definición del método ir a página previa
 
      paginator.nextPage = function() {
        if (paginator.page < paginator.numPages ) {
          paginator.page += 1;
        }
      };//Definición del método ir a página siguiente
 
      paginator.toPageId = function(id) {
        if (id >= 0 && id <= paginator.numPages ) {
          paginator.page = id;
        }
      };//Definición del método seleccionar página "Según el click"
 
      paginator.setRecords = function(id) {
        if (id >= 0 ) {
          paginator.perPage = id;
        }
      };//Definición del método establecer records mostrados por página
 
      return paginator;
    };
 
    return pagination;
  });
 
  paginationModule.filter('range', function () {
        return function (input, total) {
            total = parseInt(total);
            for (var i = 0; i < total; i++) {
                input.push(i);
            }
            return input;
        };
    });// Filter range que nos crea un array segun un numero dado ej: num 3, array = [1,2,3]    
 
})();

