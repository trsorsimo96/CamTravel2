(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyAgencyController', CompanyAgencyController);

    CompanyAgencyController.$inject = ['CompanyAgency', 'CompanyAgencySearch'];

    function CompanyAgencyController(CompanyAgency, CompanyAgencySearch) {

        var vm = this;

        vm.companyAgencies = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CompanyAgency.query(function(result) {
                vm.companyAgencies = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CompanyAgencySearch.query({query: vm.searchQuery}, function(result) {
                vm.companyAgencies = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
