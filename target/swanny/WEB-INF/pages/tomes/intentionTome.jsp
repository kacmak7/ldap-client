<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.intention-tome"/></title>
    <meta name="heading" content="<fmt:message key='print.intention-tome'/>"/>
    <meta name="menu" content="PrintMenu"/>
    
    <link rel='stylesheet' href='${ctx}/styles/fullcalendar3.3.1.min.css' />
    <link rel='stylesheet' href='${ctx}/styles/fullcalendar3.3.1.print.min.css' media="print"/>
    
    <%-- <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    
    <script type="text/javascript" src="${ctx}/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> --%>
    
    <%-- <script type="text/javascript" src="${ctx}/scripts/jquery/jquery-ui-1.12.1.min.js"></script> --%>
    
    <script type="text/javascript" src="${ctx}/scripts/fullcalendar3.3.1/moment.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/fullcalendar3.3.1/fullcalendar.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/fullcalendar3.3.1/pl.js"></script>
    
    <style type="text/css">
    	.fc-datePickerButton-button {
    		width: 120px;
    	}
    </style>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/intentions.css">
</head>

<div id="calendar"></div>
<script type="text/javascript">
$(document).ready(function() {
	$('#calendar').fullCalendar({
		customButtons: {
			printButton: {
				text: "<fmt:message key='button.print'/>",
                click: function () {
                	print_calendar();
                }
			},
			datePickerButton: {
				text: "<fmt:message key='tomes.intentionTome.goto'/>",
                click: function () {
                    var $btnCustom = $('.fc-datePickerButton-button'); // name of custom  button in the generated code
                    $btnCustom.after('<input type="text" id="hiddenDate" class="datepicker"/>');

                    $("#hiddenDate").datepicker({
                        showOn: "button",
                        changeYear: true,

                        dateFormat:"yy-mm-dd",
                        onSelect: function (dateText, inst) {
                            $('#calendar').fullCalendar('gotoDate', dateText);
                        },
                    });

                    var $btnDatepicker = $(".ui-datepicker-trigger"); // name of the generated datepicker UI 
                    //Below are required for manipulating dynamically created datepicker on custom button click
                    $("#hiddenDate").show().focus().hide();
                    $btnDatepicker.trigger("click"); //dynamically generated button for datepicker when clicked on input textbox
                    $btnDatepicker.hide();
                    $btnDatepicker.remove();
                    $("input.datepicker").not(":first").remove();//dynamically appended every time on custom button click

                }
            }
	    },
		header: {
			left: 'prev,next today datePickerButton printButton',
			center: 'title',
			right: 'agendaWeek,listWeek'
		},
		defaultView: 'agendaWeek',
		minTime: '06:00:00',
		maxTime: '24:00:00',
		navLinks: true, // can click day/week names to navigate views
		editable: true,
		eventLimit: true, // allow "more" link when too many events
		events: '${ctx}/tomes/intentions.json',
		defaultDate: '${defaultDate}',
		timezone: 'local',
		defaultTimedEventDuration: '00:30:00',
		
		/* businessHours: [ // specify an array instead
		    {
		        dow: [ 1, 2, 3 ], // Monday, Tuesday, Wednesday
		        start: '08:00', // 8am
		        end: '18:00' // 6pm
		    },
		    {
		        dow: [ 4, 5 ], // Thursday, Friday
		        start: '10:00', // 10am
		        end: '16:00' // 4pm
		    }
		], */
		
		dayClick: function(date, jsEvent, view) {
			var d = new Date(date);
			if (d.getHours() == 1 || d.getHours() == 2)
				window.open( '${ctx}/tomes/editIntention.html?edit=true&allDay=true&date='+date, "Dodaj intencję", 
					"status = 1, height = 700, width = 800, resizable = 1" );
			else
				window.open( '${ctx}/tomes/editIntention.html?edit=true&date='+date, "Dodaj intencję", 
					"status = 1, height = 700, width = 800, resizable = 1" );
	    },
	    displayEventTime: false,
	    eventClick: function(event, jsEvent, view) {
	    	if (typeof event.id !== 'undefined') {
	    	    if (event.allDay === true)
    	    	{
	    	    	window.open( '${ctx}/tomes/annotationsList.html?list=true&allDay=true&date='+Date.parse(event.start._i)+'&id=' + event.id, "Lista intencji", 
						"status = 1, height = 800, width = 1200, resizable = 1" );
    	    	}
	    	    else
    	    	{
		    		window.open( '${ctx}/tomes/editIntention.html?edit=true&date='+Date.parse(event.start._i)+'&id=' + event.id, "Lista intencji", 
						"status = 1, height = 800, width = 1200, resizable = 1" );
	    	    }
	    	} else {
				window.open( '${ctx}/tomes/intentionsList.html?list=true&date='+Date.parse(event.start._i), "Lista intencji", 
					"status = 1, height = 800, width = 1200, resizable = 1" );
	    	}
	    	return false;
	    },
	    allDayText: 'Adnotacje',
	    views: {
	    	listWeek: { // name of view
	    		displayEventTime: true
	        },
	        day: {
	        	displayEventTime: true
	        }
	    },
	    eventRender: function(event, element) {
	        element.prop("title", event.info);
	        var $title = element.find( '.fc-title' );
	    	$title.html( $title.text() );
	    }
	});
});

function print_calendar() {
    window.print(); 
}


</script>