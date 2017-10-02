/**
 * Copyright (C) 2001-2017 by RapidMiner and the contributors
 * 
 * Complete list of developers available at our web site:
 * 
 * http://rapidminer.com
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
*/
package com.rapid_i.repository.wsimport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 */
@WebService(name = "ProcessService", targetNamespace = "http://service.web.rapidanalytics.de/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ProcessService {


    /**
     * Execute process cron execution response.
     *
     * @param processLocation the process location
     * @param cronExpression  the cron expression
     * @param start           the start
     * @param end             the end
     * @param processContext  the process context
     * @return returns com.rapid_i.repository.wsimport.ExecutionResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "executeProcessCron", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.ExecuteProcessCron")
    @ResponseWrapper(localName = "executeProcessCronResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.ExecuteProcessCronResponse")
    public ExecutionResponse executeProcessCron(
        @WebParam(name = "processLocation", targetNamespace = "")
        String processLocation,
        @WebParam(name = "cronExpression", targetNamespace = "")
        String cronExpression,
        @WebParam(name = "start", targetNamespace = "")
        XMLGregorianCalendar start,
        @WebParam(name = "end", targetNamespace = "")
        XMLGregorianCalendar end,
        @WebParam(name = "processContext", targetNamespace = "")
        ProcessContextWrapper processContext);

    /**
     * Gets process ids for job id.
     *
     * @param jobId the job id
     * @return returns java.util.List<java.lang.Integer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProcessIdsForJobId", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.GetProcessIdsForJobId")
    @ResponseWrapper(localName = "getProcessIdsForJobIdResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.GetProcessIdsForJobIdResponse")
    public List<Integer> getProcessIdsForJobId(
        @WebParam(name = "jobId", targetNamespace = "")
        int jobId);

    /**
     * Stop process response.
     *
     * @param scheduledProcessId the scheduled process id
     * @return returns com.rapid_i.repository.wsimport.Response
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "stopProcess", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.StopProcess")
    @ResponseWrapper(localName = "stopProcessResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.StopProcessResponse")
    public Response stopProcess(
        @WebParam(name = "scheduledProcessId", targetNamespace = "")
        int scheduledProcessId);

    /**
     * Gets running processes info.
     *
     * @param scheduledProcessId the scheduled process id
     * @return returns com.rapid_i.repository.wsimport.ProcessResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getRunningProcessesInfo", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.GetRunningProcessesInfo")
    @ResponseWrapper(localName = "getRunningProcessesInfoResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.GetRunningProcessesInfoResponse")
    public ProcessResponse getRunningProcessesInfo(
        @WebParam(name = "scheduledProcessId", targetNamespace = "")
        int scheduledProcessId);

    /**
     * Execute process simple execution response.
     *
     * @param processLocation the process location
     * @param executionTime   the execution time
     * @param processContext  the process context
     * @return returns com.rapid_i.repository.wsimport.ExecutionResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "executeProcessSimple", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.ExecuteProcessSimple")
    @ResponseWrapper(localName = "executeProcessSimpleResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.ExecuteProcessSimpleResponse")
    public ExecutionResponse executeProcessSimple(
        @WebParam(name = "processLocation", targetNamespace = "")
        String processLocation,
        @WebParam(name = "executionTime", targetNamespace = "")
        XMLGregorianCalendar executionTime,
        @WebParam(name = "processContext", targetNamespace = "")
        ProcessContextWrapper processContext);

    /**
     * Gets running processes.
     *
     * @param since the since
     * @return returns java.util.List<java.lang.Integer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getRunningProcesses", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.GetRunningProcesses")
    @ResponseWrapper(localName = "getRunningProcessesResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.GetRunningProcessesResponse")
    public List<Integer> getRunningProcesses(
        @WebParam(name = "since", targetNamespace = "")
        XMLGregorianCalendar since);

    /**
     * Cancel trigger response.
     *
     * @param triggerName the trigger name
     * @return returns com.rapid_i.repository.wsimport.Response
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelTrigger", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.CancelTrigger")
    @ResponseWrapper(localName = "cancelTriggerResponse", targetNamespace = "http://service.web.rapidanalytics.de/", className = "com.rapid_i.repository.wsimport.CancelTriggerResponse")
    public Response cancelTrigger(
        @WebParam(name = "triggerName", targetNamespace = "")
        String triggerName);

}
