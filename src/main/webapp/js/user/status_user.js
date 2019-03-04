var desativa_user = function(id){
	$.ajax({
	    url: '/api/user/desativa/' + id,
	    type: 'PUT',
	    success: function(result) {
	    	location.reload();
	    }
	});
}

var ativa_user = function(id){
	$.ajax({
	    url: '/api/user/ativa/' + id,
	    type: 'PUT',
	    success: function(result) {
	    	location.reload();
	    }
	});
}
