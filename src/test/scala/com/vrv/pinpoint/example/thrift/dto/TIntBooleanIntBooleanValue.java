/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.vrv.pinpoint.example.thrift.dto;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-08-24")
public class TIntBooleanIntBooleanValue implements org.apache.thrift.TBase<TIntBooleanIntBooleanValue, TIntBooleanIntBooleanValue._Fields>, java.io.Serializable, Cloneable, Comparable<TIntBooleanIntBooleanValue> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TIntBooleanIntBooleanValue");

  private static final org.apache.thrift.protocol.TField INT_VALUE1_FIELD_DESC = new org.apache.thrift.protocol.TField("intValue1", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField BOOL_VALUE1_FIELD_DESC = new org.apache.thrift.protocol.TField("boolValue1", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField INT_VALUE2_FIELD_DESC = new org.apache.thrift.protocol.TField("intValue2", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField BOOL_VALUE2_FIELD_DESC = new org.apache.thrift.protocol.TField("boolValue2", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TIntBooleanIntBooleanValueStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TIntBooleanIntBooleanValueTupleSchemeFactory();

  private int intValue1; // required
  private boolean boolValue1; // required
  private int intValue2; // required
  private boolean boolValue2; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    INT_VALUE1((short)1, "intValue1"),
    BOOL_VALUE1((short)2, "boolValue1"),
    INT_VALUE2((short)3, "intValue2"),
    BOOL_VALUE2((short)4, "boolValue2");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // INT_VALUE1
          return INT_VALUE1;
        case 2: // BOOL_VALUE1
          return BOOL_VALUE1;
        case 3: // INT_VALUE2
          return INT_VALUE2;
        case 4: // BOOL_VALUE2
          return BOOL_VALUE2;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __INTVALUE1_ISSET_ID = 0;
  private static final int __BOOLVALUE1_ISSET_ID = 1;
  private static final int __INTVALUE2_ISSET_ID = 2;
  private static final int __BOOLVALUE2_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INT_VALUE1, new org.apache.thrift.meta_data.FieldMetaData("intValue1", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BOOL_VALUE1, new org.apache.thrift.meta_data.FieldMetaData("boolValue1", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.INT_VALUE2, new org.apache.thrift.meta_data.FieldMetaData("intValue2", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BOOL_VALUE2, new org.apache.thrift.meta_data.FieldMetaData("boolValue2", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TIntBooleanIntBooleanValue.class, metaDataMap);
  }

  public TIntBooleanIntBooleanValue() {
  }

  public TIntBooleanIntBooleanValue(
    int intValue1,
    boolean boolValue1,
    int intValue2,
    boolean boolValue2)
  {
    this();
    this.intValue1 = intValue1;
    setIntValue1IsSet(true);
    this.boolValue1 = boolValue1;
    setBoolValue1IsSet(true);
    this.intValue2 = intValue2;
    setIntValue2IsSet(true);
    this.boolValue2 = boolValue2;
    setBoolValue2IsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TIntBooleanIntBooleanValue(TIntBooleanIntBooleanValue other) {
    __isset_bitfield = other.__isset_bitfield;
    this.intValue1 = other.intValue1;
    this.boolValue1 = other.boolValue1;
    this.intValue2 = other.intValue2;
    this.boolValue2 = other.boolValue2;
  }

  public TIntBooleanIntBooleanValue deepCopy() {
    return new TIntBooleanIntBooleanValue(this);
  }

  @Override
  public void clear() {
    setIntValue1IsSet(false);
    this.intValue1 = 0;
    setBoolValue1IsSet(false);
    this.boolValue1 = false;
    setIntValue2IsSet(false);
    this.intValue2 = 0;
    setBoolValue2IsSet(false);
    this.boolValue2 = false;
  }

  public int getIntValue1() {
    return this.intValue1;
  }

  public void setIntValue1(int intValue1) {
    this.intValue1 = intValue1;
    setIntValue1IsSet(true);
  }

  public void unsetIntValue1() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __INTVALUE1_ISSET_ID);
  }

  /** Returns true if field intValue1 is set (has been assigned a value) and false otherwise */
  public boolean isSetIntValue1() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __INTVALUE1_ISSET_ID);
  }

  public void setIntValue1IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __INTVALUE1_ISSET_ID, value);
  }

  public boolean isBoolValue1() {
    return this.boolValue1;
  }

  public void setBoolValue1(boolean boolValue1) {
    this.boolValue1 = boolValue1;
    setBoolValue1IsSet(true);
  }

  public void unsetBoolValue1() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __BOOLVALUE1_ISSET_ID);
  }

  /** Returns true if field boolValue1 is set (has been assigned a value) and false otherwise */
  public boolean isSetBoolValue1() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __BOOLVALUE1_ISSET_ID);
  }

  public void setBoolValue1IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __BOOLVALUE1_ISSET_ID, value);
  }

  public int getIntValue2() {
    return this.intValue2;
  }

  public void setIntValue2(int intValue2) {
    this.intValue2 = intValue2;
    setIntValue2IsSet(true);
  }

  public void unsetIntValue2() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __INTVALUE2_ISSET_ID);
  }

  /** Returns true if field intValue2 is set (has been assigned a value) and false otherwise */
  public boolean isSetIntValue2() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __INTVALUE2_ISSET_ID);
  }

  public void setIntValue2IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __INTVALUE2_ISSET_ID, value);
  }

  public boolean isBoolValue2() {
    return this.boolValue2;
  }

  public void setBoolValue2(boolean boolValue2) {
    this.boolValue2 = boolValue2;
    setBoolValue2IsSet(true);
  }

  public void unsetBoolValue2() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __BOOLVALUE2_ISSET_ID);
  }

  /** Returns true if field boolValue2 is set (has been assigned a value) and false otherwise */
  public boolean isSetBoolValue2() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __BOOLVALUE2_ISSET_ID);
  }

  public void setBoolValue2IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __BOOLVALUE2_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case INT_VALUE1:
      if (value == null) {
        unsetIntValue1();
      } else {
        setIntValue1((Integer)value);
      }
      break;

    case BOOL_VALUE1:
      if (value == null) {
        unsetBoolValue1();
      } else {
        setBoolValue1((Boolean)value);
      }
      break;

    case INT_VALUE2:
      if (value == null) {
        unsetIntValue2();
      } else {
        setIntValue2((Integer)value);
      }
      break;

    case BOOL_VALUE2:
      if (value == null) {
        unsetBoolValue2();
      } else {
        setBoolValue2((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case INT_VALUE1:
      return getIntValue1();

    case BOOL_VALUE1:
      return isBoolValue1();

    case INT_VALUE2:
      return getIntValue2();

    case BOOL_VALUE2:
      return isBoolValue2();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case INT_VALUE1:
      return isSetIntValue1();
    case BOOL_VALUE1:
      return isSetBoolValue1();
    case INT_VALUE2:
      return isSetIntValue2();
    case BOOL_VALUE2:
      return isSetBoolValue2();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TIntBooleanIntBooleanValue)
      return this.equals((TIntBooleanIntBooleanValue)that);
    return false;
  }

  public boolean equals(TIntBooleanIntBooleanValue that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_intValue1 = true;
    boolean that_present_intValue1 = true;
    if (this_present_intValue1 || that_present_intValue1) {
      if (!(this_present_intValue1 && that_present_intValue1))
        return false;
      if (this.intValue1 != that.intValue1)
        return false;
    }

    boolean this_present_boolValue1 = true;
    boolean that_present_boolValue1 = true;
    if (this_present_boolValue1 || that_present_boolValue1) {
      if (!(this_present_boolValue1 && that_present_boolValue1))
        return false;
      if (this.boolValue1 != that.boolValue1)
        return false;
    }

    boolean this_present_intValue2 = true;
    boolean that_present_intValue2 = true;
    if (this_present_intValue2 || that_present_intValue2) {
      if (!(this_present_intValue2 && that_present_intValue2))
        return false;
      if (this.intValue2 != that.intValue2)
        return false;
    }

    boolean this_present_boolValue2 = true;
    boolean that_present_boolValue2 = true;
    if (this_present_boolValue2 || that_present_boolValue2) {
      if (!(this_present_boolValue2 && that_present_boolValue2))
        return false;
      if (this.boolValue2 != that.boolValue2)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + intValue1;

    hashCode = hashCode * 8191 + ((boolValue1) ? 131071 : 524287);

    hashCode = hashCode * 8191 + intValue2;

    hashCode = hashCode * 8191 + ((boolValue2) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TIntBooleanIntBooleanValue other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetIntValue1()).compareTo(other.isSetIntValue1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIntValue1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.intValue1, other.intValue1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBoolValue1()).compareTo(other.isSetBoolValue1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBoolValue1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.boolValue1, other.boolValue1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIntValue2()).compareTo(other.isSetIntValue2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIntValue2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.intValue2, other.intValue2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBoolValue2()).compareTo(other.isSetBoolValue2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBoolValue2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.boolValue2, other.boolValue2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TIntBooleanIntBooleanValue(");
    boolean first = true;

    sb.append("intValue1:");
    sb.append(this.intValue1);
    first = false;
    if (!first) sb.append(", ");
    sb.append("boolValue1:");
    sb.append(this.boolValue1);
    first = false;
    if (!first) sb.append(", ");
    sb.append("intValue2:");
    sb.append(this.intValue2);
    first = false;
    if (!first) sb.append(", ");
    sb.append("boolValue2:");
    sb.append(this.boolValue2);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TIntBooleanIntBooleanValueStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TIntBooleanIntBooleanValueStandardScheme getScheme() {
      return new TIntBooleanIntBooleanValueStandardScheme();
    }
  }

  private static class TIntBooleanIntBooleanValueStandardScheme extends org.apache.thrift.scheme.StandardScheme<TIntBooleanIntBooleanValue> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TIntBooleanIntBooleanValue struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // INT_VALUE1
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.intValue1 = iprot.readI32();
              struct.setIntValue1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BOOL_VALUE1
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.boolValue1 = iprot.readBool();
              struct.setBoolValue1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // INT_VALUE2
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.intValue2 = iprot.readI32();
              struct.setIntValue2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BOOL_VALUE2
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.boolValue2 = iprot.readBool();
              struct.setBoolValue2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TIntBooleanIntBooleanValue struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(INT_VALUE1_FIELD_DESC);
      oprot.writeI32(struct.intValue1);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BOOL_VALUE1_FIELD_DESC);
      oprot.writeBool(struct.boolValue1);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(INT_VALUE2_FIELD_DESC);
      oprot.writeI32(struct.intValue2);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BOOL_VALUE2_FIELD_DESC);
      oprot.writeBool(struct.boolValue2);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TIntBooleanIntBooleanValueTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TIntBooleanIntBooleanValueTupleScheme getScheme() {
      return new TIntBooleanIntBooleanValueTupleScheme();
    }
  }

  private static class TIntBooleanIntBooleanValueTupleScheme extends org.apache.thrift.scheme.TupleScheme<TIntBooleanIntBooleanValue> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TIntBooleanIntBooleanValue struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetIntValue1()) {
        optionals.set(0);
      }
      if (struct.isSetBoolValue1()) {
        optionals.set(1);
      }
      if (struct.isSetIntValue2()) {
        optionals.set(2);
      }
      if (struct.isSetBoolValue2()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetIntValue1()) {
        oprot.writeI32(struct.intValue1);
      }
      if (struct.isSetBoolValue1()) {
        oprot.writeBool(struct.boolValue1);
      }
      if (struct.isSetIntValue2()) {
        oprot.writeI32(struct.intValue2);
      }
      if (struct.isSetBoolValue2()) {
        oprot.writeBool(struct.boolValue2);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TIntBooleanIntBooleanValue struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.intValue1 = iprot.readI32();
        struct.setIntValue1IsSet(true);
      }
      if (incoming.get(1)) {
        struct.boolValue1 = iprot.readBool();
        struct.setBoolValue1IsSet(true);
      }
      if (incoming.get(2)) {
        struct.intValue2 = iprot.readI32();
        struct.setIntValue2IsSet(true);
      }
      if (incoming.get(3)) {
        struct.boolValue2 = iprot.readBool();
        struct.setBoolValue2IsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
