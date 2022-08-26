Vue.component("header-bar",{
	data:function(){
		//组件中的data是通过函数返回的对象
		return {
			str2:"子组件中的数据"
		};
	},
	template:`<div class="divStyle">
			<table class="tableStyle">
				<tr>
					<td width="200" align="right" valign="middle">
						<img src="img/logo.png" class="logoImg">
					</td>
					<td>
						<label class="titleStyle">
						{{title}}
						</label>
					</td>
					<td>
						<slot></slot>
					</td>
					<td>
						<button @click="childMethod">子组件中的按钮</button>
					</td>
				</tr>
			</table>
		</div>`,
	props:["title"],
	methods:{
		childMethod:function(){
			this.$emit("my-event",this.str2);
		}
	}
});


Vue.component("page-frame",{
	template:`<div>
				<div id="header" style="width:100%; height:100px;background:pink">
					<slot name="s1"></slot>
				</div>
				<div style="width:100%; height:580px">
					<slot name="s2" v-bind:musics="songs"></slot>
				</div>
				<div id="footer" style="width:100%; height:40px;background:lightgray">{{cr}}</div>
			</div>`,
	props:["title","cr"],
	data:function(){
		return {
			songs:[
				{
				"id": 25640392,
				"name": "淋雨一直走",
				"artists": [
					{
						"id": 10562,
						"name": "张韶涵",
						"picUrl": null,
						"alias": [],
						"albumSize": 0,
						"picId": 0,
						"img1v1Url": "http://p2.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
						"img1v1": 0,
						"trans": null
					}
				],
				"album": {
					"id": 2262033,
					"name": "有形的翅膀",
					"artist": {
						"id": 0,
						"name": "",
						"picUrl": null,
						"alias": [],
						"albumSize": 0,
						"picId": 0,
						"img1v1Url": "http://p2.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
						"img1v1": 0,
						"trans": null
					},
					"publishTime": 1349971200007,
					"size": "11",
					"copyrightId": "684010",
					"status": "3",
					"picId": 559651418577639,
					"alia": null,
					"mark": "0",
					"type": null,
					"blurPicUrl": null,
					"pic": 0,
					"picUrl": null,
					"description": null,
					"tags": null,
					"company": null
				},
				"duration": 204906,
				"copyrightId": 684010,
				"status": 0,
				"alias": [],
				"rtype": 0,
				"ftype": 0,
				"mvid": 108,
				"fee": 8,
				"mark": 512,
				"rurl": null
			}, 
				{
				"id": 139808,
				"name": "快乐崇拜",
				"artists": [
					{
						"id": 4723,
						"name": "潘玮柏",
						"picUrl": null,
						"alias": [],
						"albumSize": 0,
						"picId": 0,
						"img1v1Url": "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
						"img1v1": 0,
						"trans": null
					}, {
						"id": 10562,
						"name": "张韶涵",
						"picUrl": null,
						"alias": [],
						"albumSize": 0,
						"picId": 0,
						"img1v1Url": "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
						"img1v1": 0,
						"trans": null
					}
				],
				"album": {
					"id": 13935,
					"name": "WU HA",
					"artist": {
						"id": 0,
						"name": "",
						"picUrl": null,
						"alias": [],
						"albumSize": 0,
						"picId": 0,
						"img1v1Url": "http://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
						"img1v1": 0,
						"trans": null
					},
					"publishTime": 1094169600000,
					"size": "10",
					"copyrightId": "7003",
					"status": "3",
					"picId": 109951165549352126,
					"alia": null,
					"mark": "0",
					"type": null,
					"blurPicUrl": null,
					"pic": 0,
					"picUrl": null,
					"description": null,
					"tags": null,
					"company": null
				},
				"duration": 205306,
				"copyrightId": 7003,
				"status": 0,
				"alias": [],
				"rtype": 0,
				"ftype": 0,
				"mvid": 0,
				"fee": 8,
				"mark": 8192,
				"rurl": null
			}
			]
		};
	}
});
