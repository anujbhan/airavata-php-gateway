#
# Autogenerated by Thrift Compiler (0.9.3)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException

from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class ComputationalResourceSchedulingModel:
  """
  ComputationalResourceSchedulingModel:



  Attributes:
   - resourceHostId
   - totalCPUCount
   - nodeCount
   - numberOfThreads
   - queueName
   - wallTimeLimit
   - totalPhysicalMemory
   - chessisNumber
   - staticWorkingDir
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'resourceHostId', None, None, ), # 1
    (2, TType.I32, 'totalCPUCount', None, None, ), # 2
    (3, TType.I32, 'nodeCount', None, None, ), # 3
    (4, TType.I32, 'numberOfThreads', None, None, ), # 4
    (5, TType.STRING, 'queueName', None, None, ), # 5
    (6, TType.I32, 'wallTimeLimit', None, None, ), # 6
    (7, TType.I32, 'totalPhysicalMemory', None, None, ), # 7
    (8, TType.STRING, 'chessisNumber', None, None, ), # 8
    (9, TType.STRING, 'staticWorkingDir', None, None, ), # 9
  )

  def __init__(self, resourceHostId=None, totalCPUCount=None, nodeCount=None, numberOfThreads=None, queueName=None, wallTimeLimit=None, totalPhysicalMemory=None, chessisNumber=None, staticWorkingDir=None,):
    self.resourceHostId = resourceHostId
    self.totalCPUCount = totalCPUCount
    self.nodeCount = nodeCount
    self.numberOfThreads = numberOfThreads
    self.queueName = queueName
    self.wallTimeLimit = wallTimeLimit
    self.totalPhysicalMemory = totalPhysicalMemory
    self.chessisNumber = chessisNumber
    self.staticWorkingDir = staticWorkingDir

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.resourceHostId = iprot.readString()
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I32:
          self.totalCPUCount = iprot.readI32()
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.I32:
          self.nodeCount = iprot.readI32()
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.I32:
          self.numberOfThreads = iprot.readI32()
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.STRING:
          self.queueName = iprot.readString()
        else:
          iprot.skip(ftype)
      elif fid == 6:
        if ftype == TType.I32:
          self.wallTimeLimit = iprot.readI32()
        else:
          iprot.skip(ftype)
      elif fid == 7:
        if ftype == TType.I32:
          self.totalPhysicalMemory = iprot.readI32()
        else:
          iprot.skip(ftype)
      elif fid == 8:
        if ftype == TType.STRING:
          self.chessisNumber = iprot.readString()
        else:
          iprot.skip(ftype)
      elif fid == 9:
        if ftype == TType.STRING:
          self.staticWorkingDir = iprot.readString()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('ComputationalResourceSchedulingModel')
    if self.resourceHostId is not None:
      oprot.writeFieldBegin('resourceHostId', TType.STRING, 1)
      oprot.writeString(self.resourceHostId)
      oprot.writeFieldEnd()
    if self.totalCPUCount is not None:
      oprot.writeFieldBegin('totalCPUCount', TType.I32, 2)
      oprot.writeI32(self.totalCPUCount)
      oprot.writeFieldEnd()
    if self.nodeCount is not None:
      oprot.writeFieldBegin('nodeCount', TType.I32, 3)
      oprot.writeI32(self.nodeCount)
      oprot.writeFieldEnd()
    if self.numberOfThreads is not None:
      oprot.writeFieldBegin('numberOfThreads', TType.I32, 4)
      oprot.writeI32(self.numberOfThreads)
      oprot.writeFieldEnd()
    if self.queueName is not None:
      oprot.writeFieldBegin('queueName', TType.STRING, 5)
      oprot.writeString(self.queueName)
      oprot.writeFieldEnd()
    if self.wallTimeLimit is not None:
      oprot.writeFieldBegin('wallTimeLimit', TType.I32, 6)
      oprot.writeI32(self.wallTimeLimit)
      oprot.writeFieldEnd()
    if self.totalPhysicalMemory is not None:
      oprot.writeFieldBegin('totalPhysicalMemory', TType.I32, 7)
      oprot.writeI32(self.totalPhysicalMemory)
      oprot.writeFieldEnd()
    if self.chessisNumber is not None:
      oprot.writeFieldBegin('chessisNumber', TType.STRING, 8)
      oprot.writeString(self.chessisNumber)
      oprot.writeFieldEnd()
    if self.staticWorkingDir is not None:
      oprot.writeFieldBegin('staticWorkingDir', TType.STRING, 9)
      oprot.writeString(self.staticWorkingDir)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.resourceHostId)
    value = (value * 31) ^ hash(self.totalCPUCount)
    value = (value * 31) ^ hash(self.nodeCount)
    value = (value * 31) ^ hash(self.numberOfThreads)
    value = (value * 31) ^ hash(self.queueName)
    value = (value * 31) ^ hash(self.wallTimeLimit)
    value = (value * 31) ^ hash(self.totalPhysicalMemory)
    value = (value * 31) ^ hash(self.chessisNumber)
    value = (value * 31) ^ hash(self.staticWorkingDir)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
