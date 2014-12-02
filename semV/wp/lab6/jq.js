$(document).ready(function() {
	var $dragging = null;
	var $st_p1x = 0;
	var $st_p1y = 0;
	var $st_p2x = 130;
	var $st_p2y = 500;
	var $over = [];
	var $container = $(".container");


	function is_in_stack(elem) {
		var $x = elem.position().left;
		var $y = elem.position().top;

		return ($x >= $st_p1x && $x <= $st_p2x 
			 ||  $x + 200 >= $st_p1x + 70 && $x + 200 <= $st_p2x + 70)
			 && $y >= $st_p1y && $y <= $st_p2y;
	}

	function move(elem, dir) {
		if(dir > 0) {
			
		} else if (dir < 0) {
			elem.animate({top: elem.position().top + 50}, 1000);
		}
	}

	function get_overlap() {
		var $dx = $dragging.position().left;
		var $dy = $dragging.position().top;
		$('.elem').each(
			function (e) {
				var $x = $(this).position().left;
				var $y = $(this).position().top;

				if($dy < $y + 50 && $dy + 50 > $y &&
						$dx + 200 > $x && $dx < $x + 200) {
					if($x != $dx && $y != $dy)
						$over.push($(this));
				}
			}
		);	
	}

	function has_lower(py) {
		var $ret_val = false;
		$('.elem').each(
			function (e) {
				var $ey = $(this).position().top;
				console.log($container.position().top + $container.height() - py);

				if(($ey - py < 10 && $ey - py > 3) || 
						($container.position().top + $container.height() - py < 14)) {
					$ret_val = true;
				}
			}
		);	
		return $ret_val;
	}

	$(document.body).on("mousemove", function(e) {
		var el_w = $('.elem').outerWidth(),
          el_h = $('.elem').outerHeight();
		if ($dragging) {
			$dragging.offset({
				top: e.pageY -  el_h / 2,
				left: e.pageX - el_w / 2
			});
			var $dx = $dragging.position().left;
			var $dy = $dragging.position().top;
			if(is_in_stack($dragging)) {
				get_overlap()
				if($over.length != 0) {
					var $ovr = $over[0];
					if($ovr.position().top + $ovr.height() > $dy)  {
						console.log("down");
						var lp = $ovr.position().top + $ovr.height();
						if(has_lower(lp) == false) {
							$ovr.stop().animate({top: "+=57px"}, 1);	
						}
					} else if($ovr.position().top < $dy + $dragging.height())  {
						console.log("up");
						$ovr.stop().animate({top: "-=57px"}, 1);
					}
					$over = [];
				}
			}
		}
	});


	$(document.body).on("mousedown", "div.elem", function (e) {
		$dragging = $(e.target);
	});

	$(document.body).on("mouseup", function (e) {
		$dragging = null;
	});
});