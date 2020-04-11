import {DeviceParameter} from "./device-parameter";

export class Device {
  id?: number;
  name: string;
  description?: string;
  secretKey?: string;
  status?: string;

  parameters?: DeviceParameter[];
  oldParameters?: DeviceParameter[];
}
