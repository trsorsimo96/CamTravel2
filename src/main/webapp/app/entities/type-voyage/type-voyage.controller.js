(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypeVoyageController', TypeVoyageController);

    TypeVoyageController.$inject = ['TypeVoyage', 'TypeVoyageSearch'];

    function TypeVoyageController(TypeVoyage, TypeVoyageSearch) {

        var vm = this;

        vm.typeVoyages = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TypeVoyage.query(function(result) {
                vm.typeVoyages = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TypeVoyageSearch.query({query: vm.searchQuery}, function(result) {
                vm.typeVoyages = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
