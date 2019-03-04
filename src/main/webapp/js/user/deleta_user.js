var deleta_user = function(id){
	$.ajax({
	    url: '/api/user/' + id,
	    type: 'DELETE',
	    success: function(result) {
	    	location.reload();
	    }
	});
}

