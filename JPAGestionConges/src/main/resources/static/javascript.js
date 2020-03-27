function CheckHandle(b){
	var arrayLength = document.getElementsByTagName('input').length;
	var a;
	for (var i = 0; i < arrayLength; i++) {
	    if(document.getElementsByTagName('input')[i].checked){
	    	a=document.getElementsByTagName('input')[i];
	    	break;
	    }
	}
	if(a==null){
		b.childNodes[1].childNodes[1].childNodes[1].checked=true;
		document.getElementById('Modifier').classList.remove("disabled");
		document.getElementById('Supprimer').classList.remove("disabled");
		b.classList.add('bg-warning');
	}else{
		a.parentElement.parentElement.parentElement.classList.remove('bg-warning');
		document.getElementById('Modifier').classList.add("disabled");
		document.getElementById('Supprimer').classList.add("disabled");
		a.checked=false;
	}
	}
// INTIALISATION  'DATE DE DEMANDE'  
function DateDemandeToday(){
	let today = new Date().toISOString().substr(0, 10);
	document.getElementById("DateDemande").value = today;
}
function DateDebutToday(){
	let today = new Date().toISOString().substr(0, 10);
	document.getElementById("DateDebut").value = today;
}
//FIN ------ INTIALISATION  'DATE DE DEMANDE' 
function setNombreDeJours(){
	var debut = Date.parse(document.getElementById('DateDebut').value);
	var reprise = Date.parse(document.getElementById('DateFin').value);
	var nombre = Math.round((reprise-debut)/86400000);
	document.getElementById('nombre_jours').value=nombre;
}
function CheckDebutDateNotEmpty(){
	if(!document.getElementById('DateDebut').value){
		alert("Séléctionnez d'abord une date de début du congé.");
		document.getElementById('DateDebut').focus();
		document.getElementById('DateDebut').classList.add("border");
		document.getElementById('DateDebut').classList.add("border-danger");
		
	}
}
function AddDeleteEditMessage(){
	if(window.location.href.includes("PersonnelAjoute") || window.location.href.includes("CongeAjoute")){
		document.getElementById("AddMessage").classList.remove("d-none");
	}
	if(window.location.href.includes("PersonnelSupprime") || window.location.href.includes("CongeSupprime") || window.location.href.includes("wrongOldPassword")){
		document.getElementById("DeleteMessage").classList.remove("d-none");
	}
	if(window.location.href.includes("PersonnelModifie") || window.location.href.includes("CongeModifie") || window.location.href.includes("AccountCredentialsUpdated")){
		document.getElementById("EditMessage").classList.remove("d-none");
	}
	if(window.location.href.includes("NbrJrsDep")){
		document.getElementById("NbrJrsMessage").classList.remove("d-none");
		document.getElementById("nombre_jours").classList.add("border");
		document.getElementById("nombre_jours").classList.add("border-danger");
	}

}
