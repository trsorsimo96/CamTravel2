(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('StateVoyageController', StateVoyageController);

    StateVoyageController.$inject = ['StateVoyage', 'StateVoyageSearch'];

    function StateVoyageController(StateVoyage, StateVoyageSearch) {

        var vm = this;

        vm.stateVoyages = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            StateVoyage.query(function(result) {
                vm.stateVoyages = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            StateVoyageSearch.query({query: vm.searchQuery}, function(result) {
                vm.stateVoyages = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
