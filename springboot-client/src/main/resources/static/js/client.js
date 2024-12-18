//獲取DOM元素
const clientList=document.getElementById('clientList');
const clientNumInput=document.getElementById('clientNum');
const clientNameInput=document.getElementById('clientName');
const clientIdInput=document.getElementById('clientId');
const clientCarInput=document.getElementById('clientCar');
const clientDayInput=document.getElementById('clientDay');
const clientCompanyInput=document.getElementById('clientCompany');
const clientMoneyInput=document.getElementById('clientMoney');
const addResult=document.getElementById('addResult');
//獲取DOM元素
const editModal = document.getElementById('editModal');
const editNumInput=document.getElementById('editNum');
const editNameInput=document.getElementById('editName');
const editIdInput=document.getElementById('editId');
const editCarInput=document.getElementById('editCar');
const editDayInput=document.getElementById('editDay');
const editCompanyInput=document.getElementById('editCompany');
const editMoneyInput=document.getElementById('editMoney');

//新增客戶
const addClient= async() =>{
const clientNum= clientNumInput.value;
const clientName=clientNameInput.value;
const clientId=clientIdInput.value;
const clientCar=clientCarInput.value;
const clientDay=clientDayInput.value;
const clientCompany=clientCompanyInput.value;
const clientMoney=clientMoneyInput.value;
	//儲存
	try{
		//建立json物件
		const clientDTO = {
			num: clientNum,
			name: clientName,
			id: clientId,
			car: clientCar,
		    day: clientDay,
			company: clientCompany,
			money: clientMoney
		};
		
		//將json物件轉json字串
		clientDTOString = JSON.stringify(clientDTO);
		console.log(clientDTOString);
		
		//POST 送出
		const response = await fetch('http://localhost:5487/rest/client',{
			method:'POST',
			headers:{
			'Content-Type':'application/json'
			},
			body: clientDTOString
		});
		//回應
		const apiResponse= await response.json();
		addResult.innerText=apiResponse.message;
				
				//重新查詢資料
				fetchClients();

			}catch(e){
				console.err(e);
			}	
		};


	//透過fetch 經由http://localhost:5487/rest/clients取得資料
	const fetchClients = async() => {
	try{
			console.log('取得clients資料');
			const response = await fetch('http://localhost:5487/rest/clients');
			const apiResponse = await response.json();
			console.log(apiResponse);
			addResult.innerText=apiResponse.message;
			const clients= apiResponse.data;
			displayClients(clients);
		}catch(e){
			console.err(e);
		}
	};
	//刪除客戶
	const deleteClient = async(clientNum) =>{
		
		try{
			const response = await fetch(`http://localhost:5487/rest/client/${clientNum}`,{
				method:'DELETE'
				});
			const apiResponse=await response.json();
			if(response.ok){
				//刪除成功,重新查詢
					fetchClients();
				
			}else{
				//const message= apiResponse.message;
				addResult.innerHTML= `${apiResponse.status} ${apiResponse.message} 請<a href="./">重新整理網頁</a>`;
			}
			
		}catch(e){
			console.err(e);
		}
	}

	//顯示所有客戶
	const displayClients = (clients) => {
		//清空clientList
		clientList.innerText='';
		//逐筆顯示客戶資料
		clients.forEach(client => {
		//建立一個<li>標籤
		const item =document.createElement('li');
		//在<li>標籤內放入資料	
		item.textContent= `客戶編號:${client.num} 客戶姓名:${client.name} 身分證字號:${client.id} 車牌號碼:${client.car} 到期日:${client.day} 保險公司:${client.company} 保費:${client.money} `;
		//------------------------------------------------------------------------------------------
		//建立一個刪除按鈕
		const deleteButton = document.createElement('button');
		deleteButton.textContent = '刪除';
		deleteButton.onclick = () => deleteClient(client.num);
		item.appendChild(deleteButton);
		//------------------------------------------------------------------------------------------
		//建立一個修改按鈕
		const updateButton = document.createElement('button');
		updateButton.textContent = '修改';
		updateButton.onclick = () =>openModal(client.num, client.name, client.id, client.car, client.day, client.company, client.money);		 
		item.appendChild(updateButton);
		//-------------------------------------------------------------------------------------------
		//將<li>(item) 放到 <ul>(clientList)	
		clientList.appendChild(item);
		});	
	}
	//打開小視窗	
	const openModal =(num,name,id,car,day,company,money) =>{
		console.log(num,name,id,car,day,company,money);
		editNumInput.value=num;
		editNameInput.value=name;	
		editIdInput.value=id;
		editCarInput.value=car;
		editDayInput.value=day;
		editCompanyInput.value=company;
		editMoneyInput.value=money;
		editModal.style.display = 'flex';
	}
	// 關閉小視窗
	const closeModal = () => {
		editModal.style.display = 'none';
	}
	//修改確認
	const confirmEdit= async()=>{
		try{
			//建立物件
			const clientDTO = {
						name: editNameInput.value,
						id: editIdInput.value,
						car: editCarInput.value,
					    day: editDayInput.value,
						company: editCompanyInput.value,
						money: editMoneyInput.value
					};
					const num=editNumInput.value;
					const response = await fetch(`http://localhost:5487/rest/client/${num}`,{
						method:'PUT',
						headers:{
							'Content-Type':'application/json'
						},
						body: JSON.stringify(clientDTO)
					});
					const apiResponse= await response.json();
					if(response.ok){
						fetchClients();
					}else{
						addResult.textContent=apiResponse.message;
					}
			
		}catch(e){
			addResult.textContent=e;
		}finally{
			closeModal();
		}
	}
	
	
	//呼叫fetchClient()方法
	fetchClients();