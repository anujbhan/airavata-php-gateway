/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

 include "application_io_models.thrift"
 include "scheduling_model.thrift"
 include "airavata_commons.thrift"
 include "status_models.thrift"

 namespace java org.apache.airavata.model.experiment
 namespace php Airavata.Model.Experiment
 namespace cpp apache.airavata.model.experiment
 namespace py apache.airavata.model.experiment

enum ExperimentType {
    SINGLE_APPLICATION,
    WORKFLOW
}

enum ExperimentSearchFields {
    EXPERIMENT_NAME,
    EXPERIMENT_DESC,
    APPLICATION_ID,
    FROM_DATE,
    TO_DATE,
    STATUS
}
/**
 * A structure holding the experiment configuration.
 *
 *
*/
struct UserConfigurationDataModel {
    1: required bool airavataAutoSchedule = 0,
    2: required bool overrideManualScheduledParams = 0,
    3: optional bool shareExperimentPublicly = 0,
    4: optional scheduling_model.ComputationalResourceSchedulingModel computationalResourceScheduling,
    5: optional bool throttleResources = 0,
    6: optional string userDN,
    7: optional bool generateCert = 0
}

/**
 * A structure holding the experiment metadata and its child models.
 *
 * userName:
 *   The user name of the targeted gateway end user on whose behalf the experiment is being created.
 *     the associated gateway identity can only be inferred from the security hand-shake so as to avoid
 *     authorized Airavata Clients mimicking an unauthorized request. If a gateway is not registered with
 *     Airavata, an authorization exception is thrown.
 *
 * experimentName:
 *   The name of the experiment as defined by the user. The name need not be unique as uniqueness is enforced
 *      by the generated experiment id.
 *
 * experimentDescription:
 *    The verbose description of the experiment. This is an optional parameter.
*/

struct ExperimentModel {
    1: required string experimentId,
    2: required string projectId,
    3: required ExperimentType experimentType = ExperimentType.SINGLE_APPLICATION;
    4: required string userName,
    5: required string experimentName,
    6: optional i64 creationTime,
    7: optional string description,
    8: optional string executionId,
    9: optional string gatewayExecutionId,
    10: optional bool enableEmailNotification,
    11: optional list<string> emailAddresses,
    12: optional UserConfigurationDataModel userConfigurationData,
    13: optional list<application_io_models.InputDataObjectType> experimentInputs,
    14: optional list<application_io_models.OutputDataObjectType> experimentOutputs,
    15: optional status_models.ExperimentStatus experimentStatus,
    16: optional list<airavata_commons.ErrorModel> errors
}

struct ExperimentSummaryModel {
    1: required string experimentId,
    2: required string projectId,
    3: optional i64 creationTime,
    4: required string userName,
    5: required string name,
    6: optional string description,
    7: optional string applicationId,
    8: optional status_models.ExperimentStatus experimentStatus,
}

struct ExperimentStatistics {
    1: required i32 allExperimentCount,
    2: required i32 completedExperimentCount,
    3: optional i32 cancelledExperimentCount,
    4: required i32 failedExperimentCount,
    5: required list<ExperimentSummaryModel> allExperiments,
    6: optional list<ExperimentSummaryModel> completedExperiments,
    7: optional list<ExperimentSummaryModel> failedExperiments,
    8: optional list<ExperimentSummaryModel> cancelledExperiments,
}
