(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ModePaymentController', ModePaymentController);

    ModePaymentController.$inject = ['ModePayment', 'ModePaymentSearch'];

    function ModePaymentController(ModePayment, ModePaymentSearch) {

        var vm = this;

        vm.modePayments = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ModePayment.query(function(result) {
                vm.modePayments = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ModePaymentSearch.query({query: vm.searchQuery}, function(result) {
                vm.modePayments = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
