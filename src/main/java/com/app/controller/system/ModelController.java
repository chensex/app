package com.app.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.basecontroller.BaseController;
import com.base.util.CommonAjax;
import com.base.util.CommonUtil;
import com.base.util.JackSonSerializeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/app/system")
public class ModelController extends BaseController{
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping(value="/sysModelList",method=RequestMethod.GET)
	public ModelAndView sysModelList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/sysModelList");
		return mv;
	}
	
	@RequestMapping(value="/sysModelList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysModelList(HttpServletRequest req){
		int page = 1;
		int pageSize = 10;
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		if(req.getParameter("rows") != null){
			pageSize = Integer.valueOf(req.getParameter("rows"));
		}
		debug("Controller分页日志:page=" + page + "pageSize=" + pageSize);
		return pageToJson(new PageInfo<Model>( repositoryService.createModelQuery().list()));
	}
	
	
	@RequestMapping(value="/addModel",method=RequestMethod.GET)
	public ModelAndView addModel(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/addModel");
		return mv;
	}
	
	/**
     * 创建模型
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, request.getParameter("modelName"));
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, request.getParameter("modelDesc"));
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(request.getParameter("modelName"));
            modelData.setKey(request.getParameter("modelKey"));

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    /**
     * 部署模型
     */
    @RequestMapping(value="/deploy",method = RequestMethod.POST)
    @ResponseBody
    public String deploy(HttpServletRequest request, HttpServletResponse response) {
    	CommonAjax<Model> ajax = new CommonAjax<Model>();
    	
    	try {
        	
        	 String modelId = request.getParameter("modelId");
        	 
        	 Model modelData = repositoryService.getModel(modelId);
        	 
        	 ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        	 
        	 byte[] bpmnBytes = null;  
        	 
        	 BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);  
        	 bpmnBytes = new BpmnXMLConverter().convertToXML(model);  
        	 String processName = modelData.getName() + ".bpmn20.xml";
        	 System.out.println(new String(bpmnBytes, "utf-8"));
        	 Deployment deployment = repositoryService.createDeployment()  
                     .name(modelData.getName())  
                     .addString(processName, new String(bpmnBytes, "utf-8")).deploy();
        	 if(deployment!=null && deployment.getId() != null){
        		 ajax.setState(CommonUtil.SUCCESS);
         		 ajax.setContent("部署成功");
        	 }else{
        		 ajax.setState(CommonUtil.NOTPASSERROR);
         		 ajax.setContent("部署失败");
        	 }
        } catch (Exception e) {
        	e.printStackTrace();
        	ajax.setState(CommonUtil.NOTPASSERROR);
    		ajax.setContent("部署失败");
        }
        return JackSonSerializeUtil.ObjectToJson(ajax);
    }
}
