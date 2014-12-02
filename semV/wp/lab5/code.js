var moveable = false;
var move_obj = document.getElementById('move');

var get_mouse = function(e){ 
    m_x = e.clientX || e.pageX; 
    m_y = e.clientY || e.pageY;
    if (moveable == true) {
    	move_obj.style.left = m_x;
    	move_obj.style.top  = m_y;
    }
};

document.addEventListener('mousemove', get_mouse, false);

var set_moveable = function() {
	moveable = true;
};

var unset_moveable = function() {
	moveable = false;
};

move_obj.onmousedown = set_moveable;
move_obj.onmouseup = unset_moveable;